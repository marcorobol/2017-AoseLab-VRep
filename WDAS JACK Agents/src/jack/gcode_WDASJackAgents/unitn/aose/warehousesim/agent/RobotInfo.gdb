<BAPI_Database
    :version  52
    :superclass  "ClosedWorld"
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
	    <BAPI_DBField =5
		:name  "bol"
	    >
	    <BAPI_DBField =6
		:name  "bor"
	    >
	    <BAPI_DBField =7
		:name  "ch"
	    >
	    <BAPI_DBField =8
		:name  "cb"
	    >
	    <BAPI_DBField =9
		:name  "ce"
	    >
	    <BAPI_DBField =10
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
			<BAPI_InternalRef
			    :ref
				<&5 >
			>
			<BAPI_InternalRef
			    :ref
				<&6 >
			>
			<BAPI_InternalRef
			    :ref
				<&7 >
			>
			<BAPI_InternalRef
			    :ref
				<&8 >
			>
			<BAPI_InternalRef
			    :ref
				<&9 >
			>
			<BAPI_InternalRef
			    :ref
				<&10 >
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
    logical String aol, aor, bol, bor, ch, cb, ce, lb;
    return getState (p, ms, lus, aol, aor, bol, bor, ch, cb, ce, lb);
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
    logical String aol, aor, bol, bor, ch, cb, ce, lb;
    return getState (p, ms, lus, aol, aor, bol, bor, ch, cb, ce, lb);
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
    logical String aol, aor, bol, bor, ch, cb, ce, lb;
    return getState (p, ms, lus, aol, aor, bol, bor, ch, cb, ce, lb);
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
    logical String aor, bol, bor, ch, cb, ce, lb;
    return getState (p, ms, lus, aol, aor, bol, bor, ch, cb, ce, lb);
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
    logical String aol, bol, bor, ch, cb, ce, lb;
    return getState (p, ms, lus, aol, aor, bol, bor, ch, cb, ce, lb);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getBoxOnLeft"
		:definition
		    <BAPI_Text
			:lab  "getBoxOnLeft"
			:val  `getBoxOnLeft(logical String bol) {
    logical int p, ms, lus; 
    logical String aol, aor, bor, ch, cb, ce, lb;
    return getState (p, ms, lus, aol, aor, bol, bor, ch, cb, ce, lb);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getBoxOnRight"
		:definition
		    <BAPI_Text
			:lab  "getBoxOnRight"
			:val  `getBoxOnRight(logical String bor) {
    logical int p, ms, lus; 
    logical String aol,aor, bol, ch, cb, ce, lb;
    return getState (p, ms, lus, aol, aor, bol, bor, ch, cb, ce, lb);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getCrossHaed"
		:definition
		    <BAPI_Text
			:lab  "getCrossHaed"
			:val  `getCrossHaed(logical String ch) {
    logical int p, ms, lus; 
    logical String aol,aor, bol, bor, cb, ce, lb;
    return getState (p, ms, lus, aol, aor, bol, bor, ch, cb, ce, lb);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getCrossBehind"
		:definition
		    <BAPI_Text
			:lab  "getCrossBehind"
			:val  `getCrossBehind(logical String cb) {
    logical int p, ms, lus; 
    logical String aol,aor, bol, bor, ch, ce, lb;
    return getState (p, ms, lus, aol, aor, bol, bor, ch, cb, ce, lb);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getCrossHere"
		:definition
		    <BAPI_Text
			:lab  "getCrossHere"
			:val  `getCrossHere(logical String ce) {
    logical int p, ms, lus; 
    logical String aol,aor, bol, bor, ch, cb, lb;
    return getState (p, ms, lus, aol, aor, bol, bor, ch, cb, ce, lb);
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
    logical int p, ms, lus; 
    logical String aol,aor, bol, bor, ch, cb, ce;
    return getState (p, ms, lus, aol, aor, bol, bor, ch, cb, ce, lb);
}
`
			:isLabelEditable  :false
		    >
	    >
	)
>
