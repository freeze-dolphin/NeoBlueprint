(ns io.sn.blueprint.commands
  (:require [clojure.java.io :as io]
            [io.sn.blueprint.gui :as gui]
            [io.sn.blueprint.utils :as utils])
  (:import (io.github.thebusybiscuit.slimefun4.libraries.dough.items CustomItemStack)
           (io.sn.blueprint.utils BlueprintUtils)
           (org.bukkit Material)
           (org.bukkit.entity Player)
           (org.bukkit.inventory ItemStack)))

(defmulti dispatch-command (fn [map] (:hook map)))

(defmethod dispatch-command :create [{:keys [sender id]}]
  (let [^Player p sender
        ^ItemStack hand (-> p .getInventory .getItemInMainHand)
        ]
    (if (nil? hand)
      (utils/send-msg p "<red>你必须拿着你想创建的蓝图的目标物品才能执行创建蓝图操作!")
      (gui/open-bp-create-gui-for p id hand)
      )
    )
  )

(defmethod dispatch-command :del [{:keys [sender id]}]
  (let [f (utils/fetch-res "storage" (str id ".yml"))]
    (if (io/delete-file f)
      (utils/send-msg sender (str "<lime>成功删除蓝图: <white>" id))
      )
    )
  )

(defmethod dispatch-command :edit [{:keys [sender id]}]
  (let [^Player p sender
        f (utils/fetch-res "storage" (str id ".yml"))]
    (if (.exist f)
      (gui/open-bp-edit-gui-for p id f)
      (utils/send-msg sender "<red>该蓝图不存在!")
      )
    )
  )

(defmethod dispatch-command :get [{:keys [sender id]}]
  (let [tg (BlueprintUtils/getTarget id)
        bp (CustomItemStack.
             Material/PAPER "&d蓝图&r"
             ["" (str "&f合成目标: &e" (utils/content tg)) (str "&f蓝图编号: &e" id)])
        ^Player p sender]
    (-> p .getInventory (.addItem bp))
    )
  )
