<BAPI_Database
    :version  52
    :superclass  "ClosedWorld"
    :doc
	<BAPI_Text
	    :hard_lab  "Doc:"
	    :lab  "Documentation"
	    :val  `/**
 * 
 */`
	>
    :name  "AgentsToCoordinate"
    :filename  "unitn/aose/warehousesim/agent/AgentsToCoordinate.gdb"
    :type  "aos.jack.ed.Database"
    :java
	<BAPI_Java
	    :pkg
		<BAPI_TextLine
		    :val  "unitn.aose.warehousesim.agent"
		>
	>
    :dbfields
	(
	    <BAPI_DBField =0
		:name  "agentName"
		:isKey  :true
	    >
	    <BAPI_DBField =1
		:name  "status"
		:type  "int"
	    >
	)
    :queries
	(
	    <BAPI_DBQuery
		:name  "get"
		:logicals
		    (
			<BAPI_InternalRef
			    :ref
				<&0 >
			>
			<BAPI_InternalRef
			    :ref
				<&1 >
			>
		    )
	    >
	)
>
