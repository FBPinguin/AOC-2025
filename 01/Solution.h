//
// Created by floris on 5/23/25.
//

#ifndef SOLUTION_H
#define SOLUTION_H
#include <fstream>
#include <vector>

struct rotation {
    char direction;
    long long number;
};

class Solution{
    std::vector<rotation> rotations;

public:
    void init(std::ifstream& input);

    int calculate_solution_1();

    int calculate_solution_2();


};

#endif //SOLUTION_H
