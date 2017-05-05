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
    :name  "RailInfo"
    :filename  "unitn/aose/warehousesim/agent/RailInfo.gdb"
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
			<BAPI_InternalRef
			    :ref
				<&3 >
			>
		    )
	    >
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
		:name  "getSharedAreas"
		:definition
		    <BAPI_Text
			:lab  "getSharedAreas"
			:val  `getSharedAreas(logical String $rail, logical String $otherRail, logical String $area){
    logical int $position;
    logical boolean $right;
    logical boolean $storage;
    //get all the areas of the given rail...
    logical int $otherPos;
    logical boolean $otherRight;
    logical boolean $otherStorage;
    //and all the rails with that has that area
    return get($rail, $position, $right, $storage, $area) && get($otherRail, $otherPos, $otherRight, $otherStorage, $area.as_string()) && $otherRail.as_string()!=$rail.as_string();
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
			:val  `getSharedAreas(String rail, logical String otherRail, logical String area){
    logical int $position;
    logical boolean $right;
    logical boolean $storage;
    logical String $area;
    //get all the areas of the given rail...
    logical String $otherRail;
    logical int $otherPos;
    logical boolean $otherRight;
    logical boolean $otherStorage;
    //and all the rails with that has that area
    return get(rail, $position, $right, $storage, $area) && get($otherRail, $otherPos, $otherRight, $otherStorage, $area.as_string()) && $otherRail.as_string()!=rail;
}
`
			:isLabelEditable  :false
		    >
	    >
	)
>
