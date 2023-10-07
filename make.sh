#!/usr/bin/env bash
set -euxo pipefail

files=$(find src/main -type f -iname "*.clj[cs]")
parallel npx squint compile --output-dir out/js {}  ::: ${files[@]}
cp mr-who.mjs out/js/src/main/
#output=$(find src/main -type f -iname "*.mjs")

# while read j
# do
#   j=$(echo ${j} | awk '{print $1}')
#   filename=$(basename -- "${j}")
#   echo "file changed: " ${filename} " recompiling"
  
#   npx squint compile ${filename} && mv "${filename%.*}.mjs" js/
  
# done <  <(inotifywait -m -e modify ${files[@]} )
