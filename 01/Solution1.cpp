//
// Created by floris on 5/23/25.
//


#include "Solution.h"

void modulos (long long &a, long long modulo) {
    while (a <= 0) {
        a += modulo;
    }
    a %= modulo;
}

int Solution::calculate_solution_1(){
    long long location = 50;
    long long acc{};
    for (auto rotation: rotations) {
        if (rotation.direction == 'R') {
            location += rotation.number;
        }
        else {
            location -= rotation.number;
        }
        modulos(location, 100);
        if (location == 0) {
            acc++;
        }
    }
    return acc;
}
