(defproject simple-mailer "0.1.1"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.draines/postal "2.0.0"]
                 [org.clojure/core.async "0.2.385"]]
  :main ^:skip-aot simple-mailer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :bin {:name "simple-mailer"
        :bin-path "."})
