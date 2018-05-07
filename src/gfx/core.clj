(ns gfx.core
  (:require [blancas.kern.core :as parser :refer :all])
  (:require [blancas.kern.lexer.java-style
             :as l
             :refer [identifier string-lit word]]))

; TODO: do not ignore comments

(defn exit-err
  [msg]
  (.println *err* msg)
  (. System exit 1))

(def p-package
  (bind [_  (word "package")
         name identifier]
        (return {:package name})))

(def p-import
  (bind [_ (word "import")
         alias (optional identifier)
         name string-lit]
        (return {:import name :alias alias})))

(def p-type
  ; TODO: handle case of a.b.c (now it losts 'c')
  ; TODO handle map[foo]bar
  (<+> (optional (l/token "[]")) identifier (optional (<+> l/dot identifier))))

(def p-enum-member
  (bind [c identifier
         t p-type]
        (return {:constructor c :type t})))

(def p-enum
  (bind [_ (word "enum")
         name identifier
         members (l/braces (many1 p-enum-member))]
        (return {:enum name :members members})))

(def p-struct-tag
  (l/lexeme (parser/between (sym* \`)
                            (sym* \`)
                            (<+> (many1 (l/none-of "`"))))))

(def p-struct-field
  (bind [name identifier
         type p-type
         tag (optional p-struct-tag)]
        (return {:name name :type type :tag tag})))

(def p-struct
  (bind [_ (word "struct")
         name identifier
         fields (l/braces (many p-struct-field))]
        (return {:struct name :fields fields})))

(def p-decls
  (many (<|> p-enum p-struct)))

(def p-toplevels
  (<*> p-package
       (many p-import)
       p-decls))

(def p-file
  (bind [_ l/trim
         xs p-toplevels
         _ eof]
        (return xs)))

(defn process
  [path]
  (try
    (->> (slurp path)
         (parser/run p-file)
         println)
    (catch java.io.IOException e
      (exit-err (.getMessage e)))))

(defn -main
  [& args]
  (if-let [[path] args]
    (process path)
    (exit-err "Expected single argument: source file path")))
