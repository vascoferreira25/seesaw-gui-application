(ns seesaw-gui-application.core
  (:gen-class)
  (:use [seesaw.core])
  (:require [seesaw.selector :as selector]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
                                        ;        Window-Builder binding       ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; This is the interesting part. Note that in MyPanel.java, the widgets we're
;; interested in have their name set with setName().
(defn identify
  "Given a root widget, find all the named widgets and set their Seesaw :id
   so they can play nicely with select and everything."
  [root]
  (doseq [w (select root [:*])]
    (if-let [n (.getName w)]
      (selector/id-of! w (keyword n))))
  root)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
                                        ;            Initial Values           ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Define a variable to change the values inside a combobox
(def gender ["Male", "Female", "Other"])

;; Define a state to change after doing something
(def progress (atom 100))

;; Set the default values to each component with name `:name`
(def defaults
  {:first-name "Laura"
   :last-name "Palmer"
   :gender "Female"
   :address "123 Main St."
   :progress-bar @progress})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
                                        ;              Functions              ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(declare submit-form simulate-send-info)

(defn submit-form
  "Simulate a send info functionality"
  [form button widget]
  (reset! progress 0)
  (text! (select form button) "Sending...")
  (future (simulate-send-info form button widget)))

(defn simulate-send-info
  "Take some time to send info. Update progress over time."
  [form button widget]
  (println "Sending info")
  (while (<= @progress 100)
    (do
      (swap! progress inc) ;; Update progress
      (value! (select form widget) @progress) ;; Show progress on progress-bar
      (Thread/sleep 100) ;; Wait some time
      ))
  (text! (select form button) "Submit") ;; Change text on button to allow to submit again
  (alert "Successfuly submited.")
  (println "Done")
  (println (str "Here is the data: " (value form))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
                                        ;            Initialization           ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; A helper to create an instance of the form, annotate it for Seesaw and do
;; some other initialization.
(defn my-form
  []
  (let [form (identify (seesaw_gui_application.MainWindow.))]
    ;; initialize the state combobox
    (config! (select form [:#gender]) :model gender)

    ;; Events - a function that should be run from the start
    ;; (future (auto-count form [:#progress-bar]))

    ;; Button binding
    (listen
     ;; If button clicked execute function
     (select form [:#submit-button]) :mouse-clicked
     (fn [e]
       (submit-form form [:#submit-button] [:#progress-bar])))
    form))

;; Now we just create the panel, initialize it to the defaults above with
;; seesaw.core/value! and show it in a dialog. Note how, besides setting the
;; names of the widgets, the code in MyForm.java is strictly for layout. All
;; behavior, etc is done in Clojure.
(defn -main [& args]
  (invoke-later
   (let [form  (value! (my-form) defaults)
         output (-> form pack! show!)]
       nil)))
