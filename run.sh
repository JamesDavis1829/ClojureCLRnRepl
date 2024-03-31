rm -r ./dependencies
classpath=$(clj -Spath)
clj -P
export CLOJURE_LOAD_PATH="${classpath}"
newpath=$(Clojure.Main ./copy_deps.clj)

find ./dependencies -type f -name '*.cljr' -exec sh -c 'mv "$1" "${1%.cljr}.clj"' _ {} \;

export CLOJURE_LOAD_PATH="${newpath}"
echo "CLOJURE_LOAD_PATH=${CLOJURE_LOAD_PATH}"
echo "Running REPL"
Clojure.Main ./run_repl.clj