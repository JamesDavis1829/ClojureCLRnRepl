(ns copy-deps
  (:require [clojure.string :as str])
  (:import [System Environment]
           [System.IO.Compression ZipFile]
           [System.IO Directory]))

(defn extract-if-jar [path]
  (when (and (str/ends-with? path ".jar") (str/includes? path "clr"))
    (let [sub-dir (str/replace path #".jar$" "")
          place-dir (str (Directory/GetCurrentDirectory) "/dependencies" sub-dir)]
      (Directory/CreateDirectory place-dir)
      (ZipFile/ExtractToDirectory path place-dir)
      place-dir)))

(defn get-deps [classpath] 
  (let [paths (str/split classpath #":")]
    (filter 
     (fn [x] (not (str/blank? x))) 
     (map extract-if-jar paths))))

(let [deps (get-deps (Environment/GetEnvironmentVariable "CLOJURE_LOAD_PATH"))]
  (println (str/join ":" deps)))