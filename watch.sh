#!/usr/bin/env bash
set -euxo pipefail
trap 'kill 0' EXIT

files=$(find src/main -type f -iname "*.clj[cs]")
parallel npx squint compile --output-dir out/js {} ::: ${files[@]}
#cp ../mr-who/out/mr_who.esm.js  out/js/src/main/mr-who.mjs
npm run postcss:watch &
npx vite dev &

while read j
do
  if [[ ! -d "${j}" ]]; then    
    j=$(echo ${j} | awk '{print $1$3}')
    echo "file changed: ${j} recompiling"
    
    npx squint compile "${j}" --output-dir out/js
  fi  
done <  <(inotifywait -m -r -e modify src/main )
