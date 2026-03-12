# helloJni 项目文档

## 项目概述

这是一个 Java Native Interface (JNI) 示例项目，用于演示如何在 IntelliJ 和 CLion 中调试 JNI 项目。该项目展示了 Java 与 C++ 代码之间的交互，通过 JNI 调用本地 C++ 函数。

### 主要技术栈
- **Java**: 使用标准 Java 原生方法调用 C++ 代码
- **C++**: 通过 JNI 实现本地函数
- **构建工具**: 
  - CMake (用于 C++ 库构建)
  - Shell 脚本 (build.sh) (用于 Linux 环境编译)
- **JNI**: Java Native Interface

### 项目结构
```
helloJni/
├── src/
│   └── Main.java          # Java 主程序入口
├── jni/
│   ├── hello.cpp          # C++ 实现文件
│   ├── Main.h             # JNI 生成的头文件
│   ├── CMakeLists.txt     # CMake 构建配置
│   ├── build.sh           # Shell 构建脚本
│   └── libHello.so        # 编译生成的共享库
├── lib/
│   └── libHello.so        # 共享库输出目录
└── README.md
```

## 构建和运行

### 前置要求
- Java 开发环境 (JDK 8+)
- C++ 编译器 (gcc/g++)
- CMake (版本 2.8+)
- JNI 开发头文件

### 构建步骤

#### 方法 1: 使用 Shell 脚本 (Linux 环境)
```bash
cd jni
bash build.sh
```

这将编译 `hello.cpp` 并生成 `libHello.so` 文件，然后将其复制到 `../lib` 目录。

#### 方法 2: 使用 CMake
```bash
cd jni
mkdir -p build
cd build
cmake ..
cmake --build .
cp libHello.so ../../lib
```

### 运行 Java 程序

```bash
cd ..
java -Djava.library.path=./lib -cp ./src Main
```

或者直接在 `src` 目录运行：
```bash
cd src
java -Djava.library.path=../lib Main
```

### 预期输出
```
Hello World!
library:./lib
Hello JNI x 1
Hello JNI x 2
Hello JNI x 3
...
Hello JNI x 10
```

## 开发规范

### JNI 头文件生成
当需要修改 Java 原生方法签名时，需要重新生成 JNI 头文件：
```bash
cd src
javac Main.java
javah -jni Main
```

这将更新 `jni/Main.h` 文件。

### 调试配置
- **CMake 构建类型**: 默认设置为 Debug 模式，包含调试符号 (`-g -ggdb`)
- **C++ 标准**: 使用 C++11 (`-std=c++11`)
- **JNI 路径**: CMake 会自动查找 JNI 包和头文件

### 注意事项
1. 确保 `libHello.so` 文件存在于 `lib` 目录或 Java 库路径中
2. Java 代码使用 `System.loadLibrary("libhello")` 加载库（注意：实际加载的是 `liblibhello.so` 或系统特定的命名）
3. 库文件名在不同操作系统上可能不同：
   - Linux: `libHello.so`
   - Windows: `Hello.dll`
   - macOS: `libHello.dylib`

### 修改本地代码后的步骤
1. 修改 `jni/hello.cpp`
2. 重新编译（使用 `build.sh` 或 CMake）
3. 确保生成的 `.so` 文件复制到 `lib` 目录
4. 重新运行 Java 程序

## 文件说明

### src/Main.java
Java 主程序，包含 `main` 方法和原生方法声明 `sayHello()`。通过 `System.loadLibrary()` 加载本地库。

### jni/hello.cpp
C++ 实现，包含 `Java_Main_sayHello` 函数。该函数打印 10 行 "Hello JNI" 消息。

### jni/Main.h
由 `javah` 工具自动生成的 JNI 头文件，定义了 Java 和 C++ 之间的接口。**不应手动编辑此文件**。

### jni/CMakeLists.txt
CMake 构建配置文件，配置了项目名称、构建类型、C++ 标准、JNI 路径和源文件。

### jni/build.sh
Shell 构建脚本，直接使用 gcc 编译 C++ 代码并生成共享库。

## 开发环境

该项目主要用于演示在以下 IDE 中调试 JNI 项目：
- **IntelliJ IDEA**: 用于 Java 代码开发和调试
- **CLion**: 用于 C++ 代码开发和调试

在 Linux 环境下可以同时使用这两个 IDE 进行混合语言调试。