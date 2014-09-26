(ns mindist.core)

(def a {:name "a"
        :following {:names #{"b", "d"}
                     :refs  '#{b, d}}})

(def b {:name "b"
        :following {:names #{"c", "f"}
                    :refs  '#{c, f}}})

(def c {:name "c"
        :following {:names #{}
                    :refs  #{}}})

(def d {:name "d"
        :following {:names #{"e", "f", "b"}
                     :refs  '#{e, f, b}}})

(def e {:name "e"
        :following {:names #{"b"}
                     :refs  '#{b}}})

(def f {:name "f"
        :following {:names #{"c", "e"}
                     :refs  '#{c, e}}})


(def tried-it (atom #{}))

(def dist (atom #{}))

(defn min-distance [start-node end-node the-count]
  (if (contains? (-> start-node :following :names) (:name end-node))
    (swap! dist conj (+ the-count 1))
    (for [user (eval(-> start-node :following :refs))]
      (if (not (contains? @tried-it (user :name)))
        (do
          (swap! tried-it conj (user :name))
          (min-distance user end-node (+ the-count 1)))))))

(defn reset []
  (do
    (reset! tried-it #{})
    (reset! dist [])))

;; @tried-it
;; @dist

;; (reset)

;; (min-distance a f 0)

;; (apply min @dist)



