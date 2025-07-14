#!/bin/bash

# 检查脚本是否有执行权限
if [ ! -x "$0" ]; then
    echo "❌ 脚本没有执行权限，正在设置..."
    chmod +x "$0"
    echo "✅ 权限设置完成"
fi

# Spring Boot 自动启动脚本
echo "🚀 正在启动 TodoList 服务..."

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

# 显示环境信息
echo "📋 环境信息:"
echo "   Java 版本: $(java -version 2>&1 | head -1)"
echo "   JAVA_HOME: $JAVA_HOME"
echo "   Maven 版本: $(mvn -version 2>&1 | head -1)"
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
echo "🌐 服务将在 http://localhost:8080 启动"
echo "📋 API 文档: http://localhost:8080/api/health"
echo "🛑 按 Ctrl+C 停止服务"
echo ""

# 使用 Maven 插件启动
mvn spring-boot:run