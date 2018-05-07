(defproject gfx "0.1.0-SNAPSHOT"
  :description "Go Functional Extensions compiler"
  :url "https://github.com/jBugman/gfx"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.blancas/kern "1.1.0"]]
  :main ^:skip-aot gfx.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [[lein-cljfmt "0.5.7"]
            [jonase/eastwood "0.2.5"]])
