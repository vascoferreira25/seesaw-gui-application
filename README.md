# seesaw-gui-application

A simple GUI made with Clojure and Seesaw.

![GUI Image](/gui.png)

## How to make one?

This GUI was made using Netbeans IDE and its inbuilt Window Builder.

Steps:
1. Create a Clojure project using `Leiningen` or `Boot`, for example in `Leiningen`:
```shell
lein new app my-gui-project
```
2. Edit your `project.clj`, add `[seesaw "LATEST"]` to your dependencies and `:java-source-paths ["src"]`. It should stay like this:
```clojure
(defproject my-gui-project "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [seesaw "LATEST"]]
  :main ^:skip-aot my-gui-project.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :java-source-paths ["src"])
```
3. Create a project in Netbeans and create a new GUI, for example `JFrame` and add the widgets you need. Avoid using `AWT` widgets, at least for me it gives errors.
4. For each component (button, progress bar, label, tree, etc.) that you will want to use, make sure to always insert a name (in its properties). For example, a textField may have as a name: `first-name`.
5. Copy the GUI `MainWindow.java` file and any images to your Clojure `my-gui-project/src/my_gui_project/`.
6. Edit the `MainWindow.java` and at the top, replace whatever `package xxxxx;` with: `package my_gui_project;`. Note that this is the name of the folder inside `/src/` folder. If you insert any image into the GUI, make sure to change the java file later and change the image path to `./image.png`.
7. Have a look at `core.clj` file and try to do your own GUI.

## Usage

To run this project, download the master and in the CMD, run the following commands:
```shell
# Extract the folder
# Go to project folder
cd ./Downloads/seesaw-gui-application-master/

# Get dependencies
lein deps

# Compile the project
lein compile

# Run the project
lein run
```
