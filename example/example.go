// Package fun provides types of the Fun language.
// See https://github.com/jBugman/fun-lang/blob/0733d55f442379cea7fedb7d2317197146b75860/func-parse/fun/types.go
package fun

import "github.com/jBugman/fun-lang/func-parse/fun/code"

// Expr is a generic s-expression.
type Expr interface {
	exprMarker()
}

// Atom is an atom.
type Atom interface {
	Expr
	atomMarker()
}

// List is a list.
type List struct {
	Pos code.Pos `json:"pos"`
	XS  []Expr   `json:"xs"`
}

func (List) exprMarker() {}

// Ident is an identifier.
type Ident struct {
	Pos code.Pos `json:"pos"`
	X   string   `json:"x"`
}

func (Ident) exprMarker() {}
func (Ident) atomMarker() {}

// Keyword is a language keyword.
type Keyword struct {
	Pos code.Pos `json:"pos"`
	X   string   `json:"x"`
}

func (Keyword) exprMarker() {}
func (Keyword) atomMarker() {}

// Integer is an int literal.
type Integer struct {
	Pos  code.Pos `json:"pos"`
	X    int      `json:"x"`
	Base int      `json:"base,omitempty"` // 0 means 10 as a default
}

func (Integer) exprMarker() {}
func (Integer) atomMarker() {}
