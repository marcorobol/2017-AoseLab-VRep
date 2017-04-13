<BAPI_Database
    :version  52
    :superclass  "OpenWorld"
    :doc
	<BAPI_Text
	    :hard_lab  "Doc:"
	    :lab  "Documentation"
	>
    :name  "RobotInfo"
    :filename  "unitn/aose/warehousesim/agent/RobotInfo.gdb"
    :type  "aos.jack.ed.Database"
    :java
	<BAPI_Java
	    :pkg
		<BAPI_TextLine
		    :val  "unitn.aose.warehousesim.agent"
		>
	    :imports
		(
		    <BAPI_TextLine
			:val  "unitn.aose.warehousesim.api.IRobot"
		    >
		)
	>
    :dbfields
	(
	    <BAPI_DBField =0
		:name  "p"
		:type  "int"
	    >
	    <BAPI_DBField =1
		:name  "ms"
		:type  "int"
	    >
	    <BAPI_DBField =2
		:name  "lus"
		:type  "int"
	    >
	    <BAPI_DBField =3
		:name  "aol"
	    >
	    <BAPI_DBField =4
		:name  "aor"
	    >
	)
    :queries
	(
	    <BAPI_DBQuery
		:name  "getState"
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
			<BAPI_InternalRef
			    :ref
				<&2 >
			>
			<BAPI_InternalRef
			    :ref
				<&3 >
			>
			<BAPI_InternalRef
			    :ref
				<&4 >
			>
		    )
	    >
	)
    :vqueries
	(
	    <BAPI_ViewQuery
		:name  "getPosition"
		:definition
		    <BAPI_Text
			:lab  "getPosition"
			:val  `getPosition (logical int p) {
    logical int ms, lus;
    logical String aol, aor;
    return getState (p, ms, lus, aol, aor);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getMovement"
		:definition
		    <BAPI_Text
			:lab  "getMovement"
			:val  `getMovement (logical int ms) {
    logical int p, lus;
    logical String aol, aor;
    return getState (p, ms, lus, aol, aor);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getLoadUnload"
		:definition
		    <BAPI_Text
			:lab  "getLoadUnload"
			:val  `getLoadUnload(logical int lus) {
    logical int p, ms;
    logical String aol, aor;
    return getState (p, ms, lus, aol, aor);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getAreaOnLeft"
		:definition
		    <BAPI_Text
			:lab  "getAreaOnLeft"
			:val  `getAreaOnLeft(logical String aol) {
    logical int p, ms, lus;
    logical String aor;
    return getState (p, ms, lus, aol, aor);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getAreaOnRight"
		:definition
		    <BAPI_Text
			:lab  "getAreaOnRight"
			:val  `getAreaOnRight(logical String aor) {
    logical int p, ms, lus; 
    logical String aol;
    return getState (p, ms, lus, aol, aor);
}
`
			:isLabelEditable  :false
		    >
	    >
	)
>
