<BAPI_Database
    :version  52
    :superclass  "ClosedWorld"
    :doc
	<BAPI_Text
	    :hard_lab  "Doc:"
	    :lab  "Documentation"
	    :val  `/**
 * Contains current robot data, specifically:
 * the position as a progressive integer along the rail
 * the movementstate (see RobotData.MS_*)
 * the load/unload state (see RobotData.LUS_*)
 * and the name of the loaded box, if any.
 */`
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
		:name  "rail"
	    >
	    <BAPI_DBField =1
		:name  "p"
		:type  "int"
	    >
	    <BAPI_DBField =2
		:name  "ms"
		:type  "int"
	    >
	    <BAPI_DBField =3
		:name  "lus"
		:type  "int"
	    >
	    <BAPI_DBField =4
		:name  "lb"
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
		:name  "getRail"
		:definition
		    <BAPI_Text
			:lab  "getRail"
			:val  `getRail(logical String rail){
    logical int p, ms, lus;
    logical String lb;
    return getState (rail, p, ms, lus, lb);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getPosition"
		:definition
		    <BAPI_Text
			:lab  "getPosition"
			:val  `getPosition (logical int p) {
    logical String rail;
    logical int ms, lus;
    logical String lb;
    return getState (rail, p, ms, lus, lb);
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
    logical String rail;
    logical int p, lus;
    logical String lb;
    return getState (rail, p, ms, lus, lb);
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
    logical String rail;
    logical int p, ms;
    logical String lb;
    return getState (rail, p, ms, lus, lb);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getLoadedBox"
		:definition
		    <BAPI_Text
			:lab  "getLoadedBox"
			:val  `getLoadedBox(logical String lb) {
    logical String rail;
    logical int p, ms, lus;
    return getState (rail, p, ms, lus, lb);
}
`
			:isLabelEditable  :false
		    >
	    >
	)
>
