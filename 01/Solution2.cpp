//
// Created by floris on 5/23/25.
//


#include "Solution.h"

void modulos2 (long long &a, long long modulo) {
    while (a < 0) {
        a += modulo;
    }
    if (a > 100) {
    }
    a %= modulo;
}

long long timeshitzero(long long location, long long lastloc) {
    int acc{};
    if (location >= 100) {
        acc += location / 100;
    }
    else if (location <= 0) {
        if (lastloc == 0) {
            acc--;
        }
        acc += location / -100 + 1;
    }
    return acc;
}

int Solution::calculate_solution_2(){
    long long location = 50;
    long long acc{};
    long long lastloc{location};
    for (auto rotation: rotations) {
        if (rotation.direction == 'R') {
            location += rotation.number;
        }
        else {
            location -= rotation.number;
        }
        acc += timeshitzero(location, lastloc);
        modulos2(location, 100);
        lastloc = location;
    }
    return acc;
}
