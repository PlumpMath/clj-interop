(ns clj-interop.wmi
  (:import [[org.jinterop.dcom.core IJIComObject JISession JIComServer IJIDispatch JIString JIVariant]
            [org.jinterop.dcom.core JIProgId]
            [org.jinterop.dcom.impls JIObjectFactory]
            [org.jinterop.dcom.impls.automation IJIDispatch]])
  (:gen-class))

; ISWbemLocator CLSID
(def cls-id "76A6415B-CB41-11d1-8B02-00600806D9B6")

(defn connect
  "Create a COM session with the specified host, and return it"
  [host domain user password]
  (let [session (JISession/createSession host user password)
        com-server (JIComServer. (JProgId/valueOf "WbemScripting.SWbemLocator") host session)
        unknown (.createInstance com-server)
        com-object (.queryInterface unknown cls-id)
        dispatch (JIObjectFactory/narrowObject (.queryInterface com-object (IJIDispatch/IID)))] dispatch
    (.useSessionSecurity session false)))

(defn run-wmi
  "Run a wmi command against the session"
  [session command])

(defn start-service
  "Start a service on the target machine"
  [session service])

(defn stop-server
  "Stop a service on the target machine"
  [session service]
  )

