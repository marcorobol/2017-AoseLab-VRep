<BAPI_Database
    :version  52
    :superclass  "ClosedWorld"
    :doc
	<BAPI_Text
	    :hard_lab  "Doc:"
	    :lab  "Documentation"
	    :val  `/**
 * Contains data on available boxes.
 * Boxes are detected only on side positions: left or right, 
 * referring respectiveoly to RobotData.PWR_LEFT and RobotData.PWR_RIGHT.
 * The boxes are indexed by such position and their name can be retrieved.
 * No box is available at a given position if no variable is bound for that position.
 */`
	>
    :name  "BoxesPerception"
    :filename  "unitn/aose/warehousesim/agent/BoxesPerception.gdb"
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
    get(position, $name);
    if($name.isBound()){
        remove(position, $name.as_string());
    }
    return true;
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "hasLeftBox"
		:type  :function
		:definition
		    <BAPI_Text
			:lab  "hasLeftBox"
			:val  `boolean hasLeftBox(){
    logical String $name;
    get(RobotData.PWR_LEFT, $name);
    return $name.isBound();
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "hasRightBox"
		:type  :function
		:definition
		    <BAPI_Text
			:lab  "hasRightBox"
			:val  `boolean hasRightBox(){
    logical String $name;
    get(RobotData.PWR_RIGHT, $name);
    return $name.isBound();
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getLeftBox"
		:definition
		    <BAPI_Text
			:lab  "getLeftBox"
			:val  `getLeftBox(logical String $name){
    return get(RobotData.PWR_LEFT, $name);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getRightBox"
		:definition
		    <BAPI_Text
			:lab  "getRightBox"
			:val  `getRightBox(logical String $name){
    return get(RobotData.PWR_RIGHT, $name);
}
`
			:isLabelEditable  :false
		    >
	    >
	)
>
