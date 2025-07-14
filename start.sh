#!/bin/bash

# 检查脚本是否有执行权限
if [ ! -x "$0" ]; then
    echo "❌ 脚本没有执行权限，正在设置..."
    chmod +x "$0"
    echo "✅ 权限设置完成"
fi

# 获取环境参数，默认为 dev
ENVIRONMENT=${1:-dev}

# 验证环境参数
case $ENVIRONMENT in
    dev|test|prod|pre)
        echo "🎯 启动环境: $ENVIRONMENT"
        ;;
    *)
        echo "❌ 错误：不支持的环境 '$ENVIRONMENT'"
        echo "💡 支持的环境: dev, test, prod, pre"
        echo "📖 使用方法:"
        echo "   ./start.sh        # 默认使用 dev 环境"
        echo "   ./start.sh dev    # 开发环境"
        echo "   ./start.sh test   # 测试环境"
        echo "   ./start.sh prod   # 生产环境"
        echo "   ./start.sh pre    # 预发布环境"
        exit 1
        ;;
esac

# Spring Boot 自动启动脚本
echo "🚀 正在启动 TodoList 服务 [$ENVIRONMENT 环境]..."

# 自动设置 JAVA_HOME（macOS）
if [[ "$OSTYPE" == "darwin"* ]]; then
    if [ -z "$JAVA_HOME" ]; then
        export JAVA_HOME=$(/usr/libexec/java_home 2>/dev/null)
        if [ $? -ne 0 ]; then
            echo "❌ 错误：未找到 Java 安装，请先安装 JDK"
            echo "💡 建议：brew install openjdk@17"
            exit 1
        fi
        echo "✅ 自动设置 JAVA_HOME: $JAVA_HOME"
    fi
fi

# 检查是否在正确的目录
if [ ! -f "pom.xml" ]; then
    echo "❌ 错误：请在项目根目录执行此脚本"
    exit 1
fi

# 检查配置文件是否存在
CONFIG_FILE="src/main/resources/application-${ENVIRONMENT}.properties"
if [ ! -f "$CONFIG_FILE" ]; then
    echo "⚠️  警告：配置文件 $CONFIG_FILE 不存在"
    echo "💡 将使用默认配置 application.properties"
    CONFIG_FILE="src/main/resources/application.properties"
else
    echo "✅ 找到配置文件: $CONFIG_FILE"
fi

# 动态读取端口号
function get_port() {
    local config_file=$1
    local port
    
    # 首先尝试从环境特定配置文件读取
    if [ -f "$config_file" ]; then
        port=$(grep "^server.port=" "$config_file" | cut -d'=' -f2 | tr -d ' ')
    fi
    
    # 如果没找到，从默认配置文件读取
    if [ -z "$port" ] && [ -f "src/main/resources/application.properties" ]; then
        port=$(grep "^server.port=" "src/main/resources/application.properties" | cut -d'=' -f2 | tr -d ' ')
    fi
    
    # 如果还是没找到，使用默认端口
    if [ -z "$port" ]; then
        port="8080"
    fi
    
    echo $port
}

# 获取当前环境的端口号
SERVER_PORT=$(get_port "$CONFIG_FILE")

# 获取 context-path（如果有的话）
function get_context_path() {
    local config_file=$1
    local context_path
    
    if [ -f "$config_file" ]; then
        context_path=$(grep "^server.servlet.context-path=" "$config_file" | cut -d'=' -f2 | tr -d ' ')
    fi
    
    if [ -z "$context_path" ] && [ -f "src/main/resources/application.properties" ]; then
        context_path=$(grep "^server.servlet.context-path=" "src/main/resources/application.properties" | cut -d'=' -f2 | tr -d ' ')
    fi
    
    echo $context_path
}

CONTEXT_PATH=$(get_context_path "$CONFIG_FILE")

# 构建完整的服务URL
if [ -n "$CONTEXT_PATH" ] && [ "$CONTEXT_PATH" != "/" ]; then
    SERVICE_URL="http://localhost:${SERVER_PORT}${CONTEXT_PATH}"
    HEALTH_URL="${SERVICE_URL}/api/health"
else
    SERVICE_URL="http://localhost:${SERVER_PORT}"
    HEALTH_URL="${SERVICE_URL}/api/health"
fi

# 检查 Java 环境
if ! command -v java &> /dev/null; then
    echo "❌ 错误：未找到 Java 环境，请先安装 JDK"
    echo "💡 当前 JAVA_HOME: $JAVA_HOME"
    exit 1
fi

# 检查 Maven 环境
if ! command -v mvn &> /dev/null; then
    echo "❌ 错误：未找到 Maven 环境，请先安装 Maven"
    echo "💡 建议：brew install maven"
    exit 1
fi

# 检查端口是否被占用
if lsof -Pi :$SERVER_PORT -sTCP:LISTEN -t >/dev/null ; then
    echo "⚠️  警告：端口 $SERVER_PORT 已被占用"
    echo "💡 请检查是否有其他服务在运行，或修改配置文件中的端口号"
    read -p "是否继续启动？(y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "❌ 启动已取消"
        exit 1
    fi
fi

# 显示环境信息
echo "📋 环境信息:"
echo "   启动环境: $ENVIRONMENT"
echo "   Java 版本: $(java -version 2>&1 | head -1)"
echo "   JAVA_HOME: $JAVA_HOME"
echo "   Maven 版本: $(mvn -version 2>&1 | head -1)"
echo "   配置文件: $(basename $CONFIG_FILE)"
echo "   服务端口: $SERVER_PORT"
if [ -n "$CONTEXT_PATH" ] && [ "$CONTEXT_PATH" != "/" ]; then
    echo "   上下文路径: $CONTEXT_PATH"
fi
echo ""

# 清理并编译项目
echo "📦 正在编译项目..."
mvn clean compile -q

if [ $? -ne 0 ]; then
    echo "❌ 编译失败，请检查代码"
    exit 1
fi

# 启动应用
echo "✅ 编译成功，正在启动服务..."
echo "🌐 服务地址: $SERVICE_URL"
echo "📋 健康检查: $HEALTH_URL"
echo "🛑 按 Ctrl+C 停止服务"
echo ""

# 使用 Maven 插件启动，指定环境
mvn spring-boot:run -Dspring-boot.run.profiles=$ENVIRONMENT