//
// Created by floris on 5/23/25.
//

#include <fstream>
#include <iostream>

#include "Solution.h"
#include "util.h"

void Solution::init(std::ifstream &input) {
  for (std::string line{}; getline(input, line);) {
    std::stringstream ss{line};
    char a;
    long long b;
    ss >> a;
    ss >> b;
    rotations.push_back({a,b});
  }
}
