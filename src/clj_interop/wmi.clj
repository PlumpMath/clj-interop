(ns clj-interop.wmi
  (:import (org.jinterop.dcom.core IJIComObject JIArray JICallBuilder JIFlags JISession JIComServer JIString JIVariant JIProgId JIClsid)
           (org.jinterop.dcom.common IJIUnreferenced JIException JISystem)
           (org.jinterop.dcom.impls JIObjectFactory)
           (org.jinterop.dcom.impls.automation IJIDispatch IJIEnumVariant)))

; ISWbemLocator CLSID
(def cls-id "76A6415B-CB41-11d1-8B02-00600806D9B6")

(defn- narrow
  [results idx]
  (JIObjectFactory/narrowObject (.getObjectAsComObject (nth results idx))))

(defn- perform-op
  [dispatch address command]
  (let [results (.callMethodA dispatch "ConnectServer" 
                              [(JIString. address) (JIVariant/OPTIONAL_PARAM) (JIVariant/OPTIONAL_PARAM) (JIVariant/OPTIONAL_PARAM)
                               (JIVariant/OPTIONAL_PARAM) (JIVariant/OPTIONAL_PARAM) (Integer. 0) (JIVariant/OPTIONAL_PARAM)])
        wbem-services-dispatch (JIObjectFactory/narrowObject (.getObjectAsComObject (nth results 0)))]
    (let [instance-results (.callMethodA wbem-services-dispatch "InstancesOf" [(JIString. "Win32_Process") (Integer. 0) (JIVariant/OPTIONAL_PARAM)])]
      (narrow instance-results 0))))

    ;(.useSessionSecurity session false)))

(defn connect
  "Create a COM session with the specified host, and return it"
  [host domain user password]
  (JIObjectFactory/narrowObject (.queryInterface 
                                  (.queryInterface 
                                    (.createInstance 
                                      (JIComServer. (JIClsid/valueOf cls-id) host 
                                        (JISession/createSession host user password))) cls-id) (IJIDispatch/IID))))

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

