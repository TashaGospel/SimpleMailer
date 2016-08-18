(ns simple-mailer.core
  (:require [postal.core :as postal]
            [clojure.core.async :as a])
  (:gen-class))

(defn get-details []
  {:username (do (print "Username: ") (flush)
                 (read-line))
   :password (do (print "Password: ") (flush)
                 (if-let [console (System/console)]
                   (apply str (seq (.readPassword console)))
                   (read-line)))
   :to       (do (print "Recipient: ") (flush)
                 (read-line))
   :subject  (do (print "Subject: ") (flush)
                 (read-line))
   :body     (do (println "Body (must be put in quotes):")
                 (read))
   :times    (do (print "Number of times: ") (flush)
                 (read))})

(defn send-email [details]
  (postal/send-message {:host "smtp.mail.yahoo.com"
                        :user (:username details)
                        :pass (:password details)
                        :ssl  true}
                       {:from    (:username details)
                        :to      (:to details)
                        :subject (:subject details)
                        :body    (:body details)}))

(defn -main [& args]
  (let [details (get-details)
        times (atom (:times details))
        threads 4
        status (atom {:active true
                      :done   0})]
    (dotimes [_ threads]
      (a/go-loop []
        (if (and (:active @status) (pos? @times))
          (do
            (swap! times dec)
            (send-email details)
            (recur))
          (swap! status #(update % :done inc)))))
    (while (:active @status)
      (if (>= (:done @status) threads)
        (swap! status #(assoc % :active false))))))

;(-main)
