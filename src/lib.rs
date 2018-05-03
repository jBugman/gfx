extern crate failure_ext;
extern crate future_rust;

use failure_ext::{Result, ResultExt};

use future_rust::fs::read_to_string;

pub fn run(path: &str) -> Result<()> {
    let source = read_to_string(path).context("failed to read source file")?;
    println!("source: {}", source);
    Ok(())
}
