(ns io.sn.blueprint.config
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [io.sn.blueprint.utils :as utils])
  (:import (de.tr7zw.nbtinjector.javassist.compiler.ast Keyword)
           (java.io IOException PushbackReader)))

(defn- load-edn
  "Load edn from an io/reader source (filename or io/resource)."
  [source]
  (with-open [r (io/reader source)]
    (try
      (edn/read (PushbackReader. r)))

    (catch IOException e
      (printf "Couldn't open '%s': %s\n" source (.getMessage e)))
    (catch RuntimeException e
      (printf "Error parsing edn file '%s': %s\n" source (.getMessage e)))))

(defn lookup [^Keyword kw]
  (kw (load-edn (utils/fetch-res "config.edn"))
      )
  )
