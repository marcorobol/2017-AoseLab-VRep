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
		:type  "int"
	    >
	    <BAPI_DBField =2
		:name  "loadUnloadState"
		:type  "int"
	    >
	    <BAPI_DBField =3
		:name  "areaOnLeftState"
	    >
	    <BAPI_DBField =4
		:name  "areaOnRightState"
	    >
	    <BAPI_DBField =5
		:name  "crossHaedState"
	    >
	    <BAPI_DBField =6
		:name  "crossBehindState"
	    >
	    <BAPI_DBField =7
		:name  "crossHereState"
	    >
	    <BAPI_DBField =8
		:name  "loadedBox"
	    >
	    <BAPI_DBField =9
		:name  "name"
	    >
	    <BAPI_DBField =10
		:name  "boxOnRight"
	    >
	    <BAPI_DBField =11
		:name  "boxOnLeft"
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
			<BAPI_InternalRef
			    :ref
				<&11 >
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
    logical int mS, lUS;
    logical String aOL, aOR, cHS, cBS, cHeS, lB, n, bOR, bOL;
    
    return getState ( position, mS, lUS, aOL, aOR, cHS, cBS, cHeS, lB, n, bOR, bOL);
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
			:val  `getMovement (logical int movement) {
    logical int p, lUS;
    logical String aOL, aOR, cHS, cBS, cHeS, lB, n, bOR, bOL;
    
    return getState ( p, movement, lUS, aOL, aOR, cHS, cBS, cHeS, lB, n, bOR, bOL);
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
			:val  `getLoadUnLoad (logical int loadUnload) {
    logical int p, mS;
    logical String aOL, aOR, cHS, cBS, cHeS, lB, n, bOR, bOL;
    
    return getState ( p, mS, loadUnload, aOL, aOR, cHS, cBS, cHeS, lB, n, bOR, bOL);
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
			:val  `getAreaOnLeft (logical String areaLeft) {
    logical int p, mS, lUS;
    logical String aOR, cHS, cBS, cHeS, lB, n, bOR, bOL;
    
    return getState ( p, mS, lUS, areaLeft, aOR, cHS, cBS, cHeS, lB, n, bOR, bOL);
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
			:val  `getAreaOnRight (logical String areaRight) {
    logical int p, mS, lUS;
    logical String aOL, cHS, cBS, cHeS, lB, n, bOR, bOL;
    
    return getState ( p, mS, lUS, aOL, areaRight, cHS, cBS, cHeS, lB, n, bOR, bOL);
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
			:val  `getCrossHaed (logical String crossHaed) {
    logical int p, mS, lUS;
    logical String aOL, aOR, cBS, cHeS, lB, n, bOR, bOL;
    
    return getState ( p, mS, lUS, aOL, aOR, crossHaed, cBS, cHeS, lB, n, bOR, bOL);
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
			:val  `getCrossBehind (logical String crossBehind) {
    logical int p, mS, lUS;
    logical String aOL, aOR, cHS, cHeS, lB, n, bOR, bOL;
    
    return getState ( p, mS, lUS, aOL, aOR, cHS, crossBehind, cHeS, lB, n, bOR, bOL);
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
			:val  `getCrossHere (logical String crossHere) {
    logical int p, mS, lUS;
    logical String aOL, aOR, cHS, cBS, lB, n, bOR, bOL;
    
    return getState ( p, mS, lUS, aOL, aOR, cHS, cBS, crossHere, lB, n, bOR, bOL);
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
			:val  `getLoadedBox (logical String lBox) {
    logical int p, mS, lUS;
    logical String aOL, aOR, cHS, cBS, cHeS, n, bOR, bOL;
    
    return getState ( p, mS, lUS, aOL, aOR, cHS, cBS, cHeS, lBox, n, bOR, bOL);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getName"
		:definition
		    <BAPI_Text
			:lab  "getName"
			:val  `getName (logical String name) {
    logical int p, mS, lUS;
    logical String aOL, aOR, cHS, cBS, cHeS, lB, bOR, bOL;
            
    return getState ( p, mS, lUS, aOL, aOR, cHS, cBS, cHeS, lB, name, bOR, bOL);
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
			:val  `getBoxOnRight (logical String bRight) {
    logical int p, mS, lUS;
    logical String aOL, aOR, cHS, cBS, cHeS, lB, n, bOL;
    
    return getState ( p, mS, lUS, aOL, aOR, cHS, cBS, cHeS, lB, n, bRight, bOL);
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
			:val  `getBoxOnLeft (logical String bLeft) {
    logical int p, mS, lUS;
    logical String aOL, aOR, cHS, cBS, cHeS, lB, n, bOR;
    
    
    return getState ( p, mS, lUS, aOL, aOR, cHS, cBS, cHeS, lB, n, bOR, bLeft);
}
`
			:isLabelEditable  :false
		    >
	    >
	)
>
