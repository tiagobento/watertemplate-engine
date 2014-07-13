prop_name_head
	: [a-zA-Z]
	;

prop_name_body_char
	: '_'
	| [0-9]
	| prop_name_head
	;
	
prop_name_body
	: prop_name_body_char
	| prop_name_body_char prop_name_body

prop_name
	: prop_name_head
	| prop_name_head prop_name_body
	;

id
	: prop_name
	| prop_name '.' id
	;

// ---------------------------------------------

prop_eval
	: '~' id '~'
	;

if
	: '~if' id ':' statements ':~'
	| '~if' id ':' statements ':else:' statements ':~'
	;


for
	: '~for' prop_name 'in' id ':' statements ':~'
	| '~for' prop_name 'in' id ':' statements ':else:' statements ':~'
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

