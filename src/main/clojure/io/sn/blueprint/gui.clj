(ns io.sn.blueprint.gui
  (:require [io.sn.blueprint.utils :as utils])
  (:import (io.github.thebusybiscuit.slimefun4.libraries.dough.items CustomItemStack)
           (io.github.thebusybiscuit.slimefun4.utils ChestMenuUtils)
           (io.sn.blueprint.utils BlueprintUtils)
           (java.io File)
           (me.mrCookieSlime.CSCoreLibPlugin.general.Inventory ChestMenu ChestMenu$MenuCloseHandler)
           (org.bukkit Material)
           (org.bukkit.configuration.file YamlConfiguration)
           (org.bukkit.entity Player)
           (org.bukkit.inventory ItemStack)))

(defn- bp-create-on-close [bpid target target-name]
  (proxy [ChestMenu$MenuCloseHandler] []
    (onClose [^Player p]
      (do
        (BlueprintUtils/createRecipe
          bpid target
          (filter #(not (nil? %))
                  (map
                    #((if (= % (CustomItemStack. Material/BLACK_STAINED_GLASS_PANE "" []))
                        nil
                        %)) (-> this .getContents))
                  ))
        (utils/send-msg p "<lime>创建成功")
        (let [itm-blueprint
              (CustomItemStack. Material/PAPER "&d蓝图&r" ["" (str "&f合成目标: &e" target-name) (str "&f蓝图编号: &e" bpid)])]
          (-> p .getInventory (.addItem itm-blueprint)))))))

(defn open-bp-create-gui-for [^Player p ^String bpid ^ItemStack target]
  (let [target-name (utils/content target)
        ^ChestMenu _
        (doto (ChestMenu. (str "&9蓝图创建 &8[&f" target-name "&8]") (.getBlankTexture ChestMenuUtils))
          (map #(.addItem % (CustomItemStack. Material/BLACK_STAINED_GLASS_PANE "" []) (.getEmpty ChestMenuUtils))
               [0 8 9 17 18 26])
          (.addMenuCloseHandler (bp-create-on-close bpid target target-name))
          (.open p)
          )])
  )

(defn- bp-edit-on-close [bpid]
  (proxy [ChestMenu$MenuCloseHandler] []
    (onClose [^Player p]
      (do
        (BlueprintUtils/editRecipe
          bpid
          (filter #(not (nil? %))
                  (map
                    #((if (= % (CustomItemStack. Material/BLACK_STAINED_GLASS_PANE "" []))
                        nil
                        %)) (-> this .getContents))
                  ))
        (utils/send-msg p "<lime>修改成功"))
      )))

(defn open-bp-edit-gui-for [^Player p ^String bpid ^File f]
  (let [target (BlueprintUtils/getTarget bpid)
        target-name (utils/content target)
        yml (YamlConfiguration/loadConfiguration f)

        ^ChestMenu _
        (doto (ChestMenu. (str "&9蓝图编辑 &8[&f" target-name "&8]") (.getBlankTexture ChestMenuUtils))
          (map #(.addItem % (CustomItemStack. Material/BLACK_STAINED_GLASS_PANE "" []) (.getEmpty ChestMenuUtils))
               [0 8 9 17 18 26])
          (map #(.addItem % (.getItemStack yml (str "recipe." %)))
               (concat (range 1 8) (range 10 17) (range 19 26))) ;; restore recipe to inv
          (.addMenuCloseHandler (bp-edit-on-close bpid))
          (.open p)
          )])
  )
