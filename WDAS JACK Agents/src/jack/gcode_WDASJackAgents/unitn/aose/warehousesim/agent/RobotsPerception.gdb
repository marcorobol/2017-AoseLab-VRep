<BAPI_Database
    :version  52
    :superclass  "ClosedWorld"
    :doc
	<BAPI_Text
	    :hard_lab  "Doc:"
	    :lab  "Documentation"
	    :val  `/**
 * Contains perception data of other robots, specifically:
 * the relative position of the robot (PositionWithRespectToMe),
 * the name of the robot and it's relative movement (MovementWithRespectedToMe).
 * The relative position and movement are mapped as integers in RobotData respectively as
 * RobotData.PWR_* and RobotData.MWR_*.
 * No robot is present at a given position if no variable is bound for that position.
 */`
	>
    :name  "RobotsPerception"
    :filename  "unitn/aose/warehousesim/agent/RobotsPerception.gdb"
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
		:name  "position"
		:isKey  :true
		:type  "int"
	    >
	    <BAPI_DBField =1
		:name  "name"
	    >
	    <BAPI_DBField =2
		:name  "movement"
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
				<&1 >
			>
			<BAPI_InternalRef
			    :ref
				<&2 >
			>
		    )
	    >
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
			<BAPI_InternalRef
			    :ref
				<&2 >
			>
		    )
	    >
	)
    :vqueries
	(
	    <BAPI_ViewQuery
		:name  "remove"
		:type  :function
		:definition
		    <BAPI_Text
			:lab  "remove"
			:val  `boolean remove(int position){
    logical String $name;
    logical int $movement;
    get(position, $name, $movement);
    if($name.isBound()){
        remove(position, $name.as_string(), $movement.as_int());
    }
    return true;
}
`
			:isLabelEditable  :false
		    >
	    >
	)
>
