use rand::Rng;
use std::time::Instant;

// Tri par insertion
fn insertion_sort(arr: &mut [i32]) {
    let len = arr.len();
    for i in 1..len {
        let key = arr[i];
        let mut j = i as isize - 1;
        while j >= 0 && arr[j as usize] > key {
            arr[j as usize + 1] = arr[j as usize];
            j -= 1;
        }
        arr[j as usize + 1] = key;
    }
}

// QuickSort
fn quick_sort(arr: &mut [i32]) {
    if arr.len() <= 1 {
        return;
    }

    let pivot = arr[arr.len() / 2];
    let mut left: Vec<i32> = Vec::new();
    let mut middle: Vec<i32> = Vec::new();
    let mut right: Vec<i32> = Vec::new();

    // Diviser les éléments en trois sous-groupes : gauche, milieu (pivot), droite
    for i in arr.iter() {
        if *i < pivot {
            left.push(*i);
        } else if *i == pivot {
            middle.push(*i);
        } else {
            right.push(*i);
        }
    }

    // Trier récursivement les sous-groupes
    quick_sort(&mut left);
    quick_sort(&mut right);

    // Combiner les sous-groupes
    arr[..left.len()].copy_from_slice(&left);
    arr[left.len()..left.len() + middle.len()].copy_from_slice(&middle);
    arr[left.len() + middle.len()..].copy_from_slice(&right);
}

// Fonction pour mesurer le temps d'exécution
fn measure_sort_time<F>(sort_function: F, arr: &mut [i32], iterations: usize) -> f64
where
    F: Fn(&mut [i32]),
{
    let mut total_time = 0.0;

    for _ in 0..iterations {
        let mut arr_copy = arr.to_vec();
        let start_time = Instant::now();
        sort_function(&mut arr_copy);
        total_time += start_time.elapsed().as_secs_f64();
    }

    total_time / iterations as f64
}

fn main() {
    let sizes = [1000, 5000, 10000, 50000, 100000];
    let iterations = 10;
    let mut rng = rand::thread_rng();

    for &size in &sizes {
        let mut arr: Vec<i32> = (0..size).map(|_| rng.gen_range(0..100000)).collect();

        // Mesure pour le tri par insertion
        let insertion_time = measure_sort_time(insertion_sort, &mut arr, iterations);
        println!("Taille {} : Tri par insertion {:.6}s", size, insertion_time);

        // Mesure pour QuickSort
        let quick_sort_time = measure_sort_time(quick_sort, &mut arr, iterations);
        println!("Taille {} : QuickSort {:.6}s", size, quick_sort_time);
    }
}
