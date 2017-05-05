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
    :name  "CrossInfo"
    :filename  "unitn/aose/warehousesim/agent/CrossInfo.gdb"
    :type  "aos.jack.ed.Database"
    :java
	<BAPI_Java
	    :pkg
		<BAPI_TextLine
		    :val  "unitn.aose.warehousesim.agent"
		>
	    :code
		(
		    <BAPI_Text
			:lab  "%TRAILING_CODE"
			:val  `
    
    /**
     * compute path from a rail/position to a destination rail/position
     * The path is a set of rail/position coordinates that have to be reached
     */
    //TODO
//<<END_BRACE>>


`
		    >
		)
	>
    :dbfields
	(
	    <BAPI_DBField =0
		:name  "rail"
		:isKey  :true
	    >
	    <BAPI_DBField =1
		:name  "position"
		:isKey  :true
		:type  "int"
	    >
	    <BAPI_DBField =2
		:name  "crossedRail"
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
		    )
	    >
	    <BAPI_DBQuery
		:name  "getCrossedRails"
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
		:name  "getCrossPosition"
		:logicals
		    (
			<BAPI_InternalRef
			    :ref
				<&1 >
			>
		    )
	    >
	)
>
