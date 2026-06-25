#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT_DIR"
trap 'make clean >/dev/null 2>&1 || true' EXIT

if [[ ! -d "tests" ]]; then
  echo "Error: tests directory not found in $ROOT_DIR" >&2
  exit 1
fi

if ! command -v java >/dev/null 2>&1; then
  echo "Error: java is not installed or not on PATH" >&2
  exit 1
fi
if ! command -v javac >/dev/null 2>&1; then
  echo "Error: javac is not installed or not on PATH" >&2
  exit 1
fi

echo "Cleaning previous build artifacts..."
make clean

echo "Compiling project..."
make build

total=0
passed=0
failed=0

for testdir in "$ROOT_DIR"/tests/*; do
  if [[ ! -d "$testdir" ]]; then
    continue
  fi
  testname="$(basename "$testdir")"
  input_file="$testdir/map.in"
  expected_file="$testdir/map.out"

  if [[ ! -f "$input_file" ]]; then
    echo "Skipping $testname: no map.in found" >&2
    continue
  fi
  if [[ ! -f "$expected_file" ]]; then
    echo "Skipping $testname: no expected map.out found" >&2
    continue
  fi

total=$((total + 1))
    printf "\n=== Test %s ===\n" "$testname"
  cp "$input_file" "$ROOT_DIR/map.in"
  rm -f "$ROOT_DIR/map.out"

  if ! make run >/dev/null 2>&1; then
    echo "Test $testname: FAILED (program execution error)"
    failed=$((failed + 1))
    continue
  fi

  if diff -u --strip-trailing-cr "$expected_file" "$ROOT_DIR/map.out" >/dev/null 2>&1; then
    echo "Test $testname: PASSED"
    passed=$((passed + 1))
  else
    echo "Test $testname: FAILED"
    echo "Diff for $testname:"
    diff -u --strip-trailing-cr "$expected_file" "$ROOT_DIR/map.out" || true
    failed=$((failed + 1))
  fi

done

rm -f "$ROOT_DIR/map.in"
rm -f "$ROOT_DIR/map.out"
make clean

printf "\nSummary: %s/%s passed, %s failed\n" "$passed" "$total" "$failed"
if [[ $failed -gt 0 ]]; then
  exit 1
fi
