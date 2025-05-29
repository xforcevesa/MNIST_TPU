#!/bin/bash

# 设置构建目录的绝对路径
BUILD_DIR="/Users/nomodeset/code/circt/build"

# 1. 导出可执行文件路径到PATH
export PATH="$BUILD_DIR/bin:$PATH"

# 2. 设置头文件路径（C/C++编译器搜索路径）
export CPATH="$BUILD_DIR/include:$CPATH"          # 通用头文件路径
export C_INCLUDE_PATH="$BUILD_DIR/include:$C_INCLUDE_PATH"       # C头文件
export CPLUS_INCLUDE_PATH="$BUILD_DIR/include:$CPLUS_INCLUDE_PATH" # C++头文件

# 3. 设置库文件路径
export LIBRARY_PATH="$BUILD_DIR/lib:$LIBRARY_PATH"   # 编译时库搜索路径
export LD_LIBRARY_PATH="$BUILD_DIR/lib:$LD_LIBRARY_PATH"   # 运行时动态库搜索路径（Linux）
export DYLD_LIBRARY_PATH="$BUILD_DIR/lib:$DYLD_LIBRARY_PATH" # macOS动态库搜索路径

# 4. 其他可能的配置（如Python模块路径）
export PYTHONPATH="$BUILD_DIR/lib:$PYTHONPATH"

# 打印验证
echo "Environment variables configured for CIRCT build:"
echo "PATH: $PATH"
echo "CPATH: $CPATH"
echo "LIBRARY_PATH: $LIBRARY_PATH"
echo "LD_LIBRARY_PATH: $LD_LIBRARY_PATH"