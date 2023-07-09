(ns io.sn.blueprint.utils
  (:require [clojure.java.io :as io]
            [io.sn.blueprint.config :as config])
  (:import (net.kyori.adventure.text Component TextComponent)
           (net.kyori.adventure.text.minimessage MiniMessage)
           (org.bukkit Bukkit)
           (org.bukkit.command CommandSender)
           (org.bukkit.inventory ItemStack)))

(defn ^Component mini [^String s] (.deserialize (MiniMessage/miniMessage) s))

(defn ^String content
  ([^TextComponent tc]
   (.content tc))
  ([^ItemStack item]
   (content (-> item .displayName))))

(defn send-msg [^CommandSender sender ^String msg]
  (.sendMessage sender (mini (str (config/lookup :prefix) msg)))
  )

(defn fetch-res [& path]
  (apply (io/file (.getPath (Bukkit/getPluginsFolder))) "NeoBlueprint" path))
