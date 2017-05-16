<BAPI_Database
    :version  52
    :superclass  "ClosedWorld"
    :doc
	<BAPI_Text
	    :hard_lab  "Doc:"
	    :lab  "Documentation"
	    :val  `/**
 * Contains information about the areas and their position on the rails
 * The RobotController will only be aware of areas and other rails as they perceive them.
 * The RobotCoordinator, instead, is aware of all the areas at initialization.
 */`
	>
    :name  "AreaInfo"
    :filename  "unitn/aose/warehousesim/agent/AreaInfo.gdb"
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
			:val  "java.util.ArrayList"
		    >
		    <BAPI_TextLine
			:val  "java.util.List"
		    >
		)
	>
    :dbfields
	(
	    <BAPI_DBField =0
		:name  "name"
		:isKey  :true
	    >
	    <BAPI_DBField =1
		:name  "position"
		:isKey  :true
		:type  "int"
	    >
	    <BAPI_DBField =2
		:name  "right"
		:isKey  :true
		:type  "boolean"
	    >
	    <BAPI_DBField =3
		:name  "storage"
		:type  "boolean"
	    >
	    <BAPI_DBField =4
		:name  "area"
	    >
	    <BAPI_DBField =5
		:name  "areaState"
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
		    )
	    >
	    <BAPI_DBQuery
		:name  "getRail"
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
				<&5 >
			>
		    )
	    >
	    <BAPI_DBQuery
		:name  "getAreas"
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
		    )
	    >
	    <BAPI_DBQuery
		:name  "getAreas"
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
				<&4 >
			>
			<BAPI_InternalRef
			    :ref
				<&5 >
			>
		    )
	    >
	    <BAPI_DBQuery
		:name  "getAreas"
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
			<BAPI_InternalRef
			    :ref
				<&4 >
			>
			<BAPI_InternalRef
			    :ref
				<&5 >
			>
		    )
	    >
	    <BAPI_DBQuery
		:name  "getArea"
		:logicals
		    (
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
		    )
	    >
	)
    :vqueries
	(
	    <BAPI_ViewQuery
		:name  "getDepositWithdrawAreas"
		:definition
		    <BAPI_Text
			:lab  "getDepositWithdrawAreas"
			:val  `getDepositWithdrawAreas(logical String $area){
    logical String _$rail;
    logical int _$position;
    logical boolean _$right;
    logical int _$areaState;
    boolean isStorage = false;
    return getAreas(_$rail, _$position, _$right, isStorage, $area, _$areaState);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getStorageAreas"
		:definition
		    <BAPI_Text
			:lab  "getStorageAreas"
			:val  `getStorageAreas(logical String $area){
    logical String _$rail;
    logical int _$position;
    logical boolean _$right;
    logical int _$areaState;
    boolean isStorage = true;
    return getAreas(_$rail, _$position, _$right, isStorage, $area, _$areaState);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getStorageAreas"
		:definition
		    <BAPI_Text
			:lab  "getStorageAreas"
			:val  `getStorageAreas(String rail, logical String $area){
    logical int _$position;
    logical boolean _$right;
    logical int _$areaState;
    boolean isStorage = true;
    return getAreas(rail, _$position, _$right, isStorage, $area, _$areaState);
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getSharedAreas"
		:definition
		    <BAPI_Text
			:lab  "getSharedAreas"
			:val  `getSharedAreas(logical String $rail, logical String $otherRail, logical String $area){
    logical int _$position;
    logical boolean _$right;
    logical boolean _$storage;
    logical int _$areaState;
    //get all the areas of the given rail...
    logical int _$otherPos;
    logical boolean _$otherRight;
    logical boolean _$otherStorage;
    logical int _$otherAreaState;
    //and all the rails with that has that area
    return get($rail, _$position, _$right, _$storage, $area, _$areaState) && 
        getRail($otherRail, _$otherPos, _$otherRight, _$otherStorage, $area.as_string(), _$otherAreaState) && 
        $otherRail.as_string()!=$rail.as_string();
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getSharedAreas"
		:definition
		    <BAPI_Text
			:lab  "getSharedAreas"
			:val  `getSharedAreas(String rail, logical String $otherRail, logical String $area){
    logical int _$position;
    logical boolean _$right;
    logical boolean _$storage;
    logical int _$areaState;
    //get all the areas of the given rail...
    logical int _$otherPos;
    logical boolean _$otherRight;
    logical boolean _$otherStorage;
    logical int _$otherAreaState;
    //and all the rails with that has that area
    return getAreas(rail, _$position, _$right, _$storage, $area, _$areaState) && 
        getRail($otherRail, _$otherPos, _$otherRight, _$otherStorage, $area.as_string(), _$otherAreaState) && 
        $otherRail.as_string()!=rail;
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "getSharedAreas"
		:definition
		    <BAPI_Text
			:lab  "getSharedAreas"
			:val  `getSharedAreas(String rail, String otherRail, logical String $area){
    logical int _$position;
    logical boolean _$right;
    logical boolean _$storage;
    logical int _$areaState;
    //get all the areas of the given rail...
    logical int _$otherPos;
    logical boolean _$otherRight;
    logical boolean _$otherStorage;
    logical int _$otherAreaState;
    //and all the rails with that has that area
    return getAreas(rail, _$position, _$right, _$storage, $area, _$areaState) && 
        getAreas(otherRail, _$otherPos, _$otherRight, _$otherStorage, $area, _$otherAreaState);        
}
`
			:isLabelEditable  :false
		    >
	    >
	    <BAPI_ViewQuery
		:name  "updateAreaState"
		:type  :function
		:definition
		    <BAPI_Text
			:lab  "updateAreaState"
			:val  `int updateAreaState(String area, int areaState){
    logical String $rail;
    logical int $position;
    logical boolean $right;
    logical boolean $storage;
    logical int $areaState;
    //get all rails with such area
    Cursor c = getRail($rail, $position, $right, $storage, area, $areaState);
    //an area can be set on different rails: store all the raildata
    List rails = new ArrayList();
    while(c.next()){
        rails.add(new Object[]{$rail.as_string(), $position.as_int(), $right.as_boolean(), $storage.as_boolean()});
    }
    //now update the rail data with the areastate
    for(int i=0; i<rails.size(); ++i){
        Object[] railData = (Object[])rails.get(i);
        add((String)railData[0], (int)railData[1], (boolean)railData[2], (boolean)railData[3], area, areaState);
    }
    return rails.size();
}
`
			:isLabelEditable  :false
		    >
	    >
	)
>