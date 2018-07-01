(defproject seesaw-gui-application "0.1.0-SNAPSHOT"
  :description "A simple GUI made with Clojure and Seesaw."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [seesaw "LATEST"]]
  :main ^:skip-aot seesaw-gui-application.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :java-source-paths ["src"])
