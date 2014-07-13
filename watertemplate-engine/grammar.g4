prop_name_head
	: Upper- or lowercase letter A through Z
	;

prop_name_body
	: _
	| prop_name_head
	;

prop_name
	: prop_name_head
	| prop_name_head prop_name_body
	;

id
	: prop_name
	| prop_name.id
	;

// ---------------------------------------------

prop_eval
	: ~id~
	;

if
	: ~if id: statements :~
	| ~if id: statements :else: statements :~
	;


for
	: ~for prop_name in id: statements :~
	| ~for prop_name in id: statements :else: statements :~
	;

text
	: Any string
	;
 
// ---------------------------------------------

statement
	: prop_eval
	| if
	| for
	| text
	| ε
	;

statements
	: statement
	| statement statements
	;

