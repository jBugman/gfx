extern crate failure_ext;
extern crate gfx_compiler;

use failure_ext::UnwrapOrExit;

fn main() {
    let path = "example/source.gfx";
    gfx_compiler::run(path).unwrap_or_exit();
}
