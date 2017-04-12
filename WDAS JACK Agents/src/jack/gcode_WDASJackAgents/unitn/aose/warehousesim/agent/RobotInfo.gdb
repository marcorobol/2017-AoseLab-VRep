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
		:name  "position"
		:type  "int"
	    >
	    <BAPI_DBField =1
		:name  "movementState"
		:type  "Object"
	    >
	    <BAPI_DBField =2
		:name  "loadUnloadState"
		:type  "Object"
	    >
	    <BAPI_DBField =3
		:name  "areaOnLeftState"
		:type  "Object"
	    >
	    <BAPI_DBField =4
		:name  "areaOnRightState"
		:type  "Object"
	    >
	    <BAPI_DBField =5
		:name  "areaState"
		:type  "Object"
	    >
	    <BAPI_DBField =6
		:name  "crossHaedState"
		:type  "Object"
	    >
	    <BAPI_DBField =7
		:name  "crossBehindState"
		:type  "Object"
	    >
	    <BAPI_DBField =8
		:name  "crossHereState"
		:type  "Object"
	    >
	    <BAPI_DBField =9
		:name  "cartAround"
		:type  "Object"
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
			:val  `getPosition (logical int position) {
    logical Object mS, lUS, aOL, aOR, aS, cHS, cBS, cHeS, cA;
    
    return getState ( position, mS, lUS, aOL, aOR, aS, cHS, cBS, cHeS, cA);
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
			:val  `getMovement (logical Object movement) {
    logical int p;
    logical Object lUS, aOL, aOR, aS, cHS, cBS, cHeS, cA;
    
    return getState ( p, movement, lUS, aOL, aOR, aS, cHS, cBS, cHeS, cA);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getLoadUnLoad"
		:definition
		    <BAPI_Text
			:lab  "getLoadUnLoad"
			:val  `getLoadUnLoad (logical Object loadUnload) {
    logical int p;
    logical Object mS, aOL, aOR, aS, cHS, cBS, cHeS, cA;
    
    return getState ( p, mS, loadUnload, aOL, aOR, aS, cHS, cBS, cHeS, cA);
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
			:val  `getAreaOnLeft (logical Object areaLeft) {
    logical int p;
    logical Object mS, lUS, aOR, aS, cHS, cBS, cHeS, cA;
    
    return getState ( p, mS, lUS, areaLeft, aOR, aS, cHS, cBS, cHeS, cA);
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
			:val  `getAreaOnRight (logical Object areaRight) {
    logical int p;
    logical Object mS, lUS, aOL, aS, cHS, cBS, cHeS, cA;
    
    return getState ( p, mS, lUS, aOL, areaRight, aS, cHS, cBS, cHeS, cA);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getAreaState"
		:definition
		    <BAPI_Text
			:lab  "getAreaState"
			:val  `getAreaState (logical Object arState) {
    logical int p;
    logical Object mS, lUS, aOL, aOR, cHS, cBS, cHeS, cA;
    
    return getState ( p, mS, lUS, aOL, aOR, arState, cHS, cBS, cHeS, cA);
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
			:val  `getCrossHaed (logical Object crossHaed) {
    logical int p;
    logical Object mS, lUS, aOL, aOR, aS, cBS, cHeS, cA;
    
    return getState ( p, mS, lUS, aOL, aOR, aS, crossHaed, cBS, cHeS, cA);
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
			:val  `getCrossBehind (logical Object crossBehind) {
    logical int p;
    logical Object mS, lUS, aOL, aOR, aS, cHS, cHeS, cA;
    
    return getState ( p, mS, lUS, aOL, aOR, aS, cHS, crossBehind, cHeS, cA);
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
			:val  `getCrossHere (logical Object crossHere) {
    logical int p;
    logical Object mS, lUS, aOL, aOR, aS, cHS, cBS, cA;
    
    return getState ( p, mS, lUS, aOL, aOR, aS, cHS, cBS, crossHere, cA);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getCartPerception"
		:definition
		    <BAPI_Text
			:lab  "getCartPerception"
			:val  `getCartPerception (logical Object cartAr) {
    logical int p;
    logical Object mS, lUS, aOL, aOR, aS, cHS, cBS, cHeS;
    
    return getState ( p, mS, lUS, aOL, aOR, aS, cHS, cBS, cHeS, cartAr);
}
`
			:isLabelEditable  :false
		    >
	    >
	)
>
