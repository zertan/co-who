#!/usr/bin/env bash
set -euxo pipefail

export NODE_ENV=production

files=$(find src/main -type f -iname "*.clj[cs]")
parallel npx squint compile --output-dir out/js {} ::: ${files[@]}
#cp ../mr-who/out/mr_who.esm.js  out/js/src/main/mr-who.mjs
npm run postcss:release
npx vite build

# while read j
# do
#   j=$(echo ${j} | awk '{print $1}')
#   filename=$(basename -- "${j}")
#   echo "file changed: " ${filename} " recompiling"
  
#   npx squint compile ${filename} && mv "${filename%.*}.mjs" js/
  
# done <  <(inotifywait -m -e modify ${files[@]} )
