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
    :name  "PerceptionInfo"
    :filename  "unitn/aose/warehousesim/agent/PerceptionInfo.gdb"
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
	    <BAPI_DBField
		:name  "position"
		:isKey  :true
		:type  "int"
	    >
	    <BAPI_DBField =0
		:name  "name"
	    >
	    <BAPI_DBField =1
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
