let courses = [
    {
        "course_prefix": "COMP",
        "course_code": 1511,
        "course_title": "Programming Fundamentals",
        "average_stars": 4.8,
        "total_reviews": 68,
        "offered_terms": ["Term 1", "Term 2", "Term 3"]
    },
    {
        "course_prefix": "COMP",
        "course_code": 1531,
        "course_title": "Software Engineering Fundamentals",
        "average_stars": 3.9,
        "total_reviews": 47,
        "offered_terms": ["Term 1", "Term 2", "Term 3"]
    },
    {
        "course_prefix": "COMP",
        "course_code": 1521,
        "course_title": "Computer Systems Fundamentals",
        "average_stars": 4,
        "total_reviews": 40,
        "offered_terms": ["Term 1", "Term 2", "Term 3"]
    },
    {
        "course_prefix": "COMP",
        "course_code": 2521,
        "course_title": "Data Structures and Algorithms",
        "average_stars": 4,
        "total_reviews": 36,
        "offered_terms": ["Summer", "Term 1", "Term 2", "Term 3"]
    },
    {
        "course_prefix": "COMP",
        "course_code": 2511,
        "course_title": "Object-Oriented Design & Programming",
        "average_stars": 3,
        "total_reviews": 33,
        "offered_terms": ["Term 1", "Term 2", "Term 3"]
    },
    {
        "course_prefix": "COMP",
        "course_code": 3311,
        "course_title": "Database Systems",
        "average_stars": 4,
        "total_reviews": 33,
        "offered_terms": ["Term 1", "Term 3"]
    }
]

window.onload = () => {
    insertCourses();    
}


/**
 * Creates DOM elements for each course and inserts it
 */
function insertCourses() {
    // Destination container for all the courses
    const container = document.body.querySelector('.results-container');
    container.innerHTML = '';

    courses.forEach((course) => {

        // This is a more verbose way of making elements in JS
        // But it skips the parsing of HTML making it more safe
        // And slightly faster
        // Also by doing it this way, you can add a delay between each add
        // and make a nice animation for loading (using css transitions)

        // The Unit Block
        const outer = document.createElement('div');
        outer.className = 'unit-container';

        // Container holding Course Code and Reviews
        const unitTitle = document.createElement('div');
        unitTitle.className = 'unit-title';
        outer.appendChild(unitTitle);

        // Course Code
        const unitCode = document.createElement('span');
        unitCode.className = 'unit-code';
        unitCode.textContent = course.course_prefix + course.course_code;
        unitTitle.appendChild(unitCode);

        // The container holding the reviews
        const reviewContainer = document.createElement('div');
        reviewContainer.className = 'review-container';

        // The Stars
        const reviewStars = document.createElement('span');
        reviewStars.className = 'review-star';
        reviewStars.textContent = '★★★★★';
        const percent = course.average_stars / 5.0 * 100;
        reviewStars.style.background = `-webkit-linear-gradient(0deg, var(--uni-purple) ${percent}%, var(--uni-light-gray) ${percent}%)`;
        reviewContainer.appendChild(reviewStars);
        // Number of reviews
        const numReviews = document.createElement('span');
        numReviews.className = 'review-label';
        numReviews.textContent = `${course.total_reviews} review${course.total_reviews != 1 ? 's' : ''}`;
        reviewContainer.appendChild(numReviews);

        unitTitle.appendChild(reviewContainer);

        // Course Description
        const courseDesc = document.createElement('span');
        courseDesc.textContent = course.course_title;
        outer.appendChild(courseDesc);

        // Add the term chips
        const chipContainer = document.createElement('div');
        chipContainer.className = 'chip-container';

        course.offered_terms.forEach((term) => {

            const chip = document.createElement('span');
            chip.textContent = term;
            chipContainer.appendChild(chip);
        });
        outer.appendChild(chipContainer);

        container.appendChild(outer);

    })
}

/**
 * Stretch Task 1
 * Handle clicking on the logo and changing its colour from blue to purple
 * 
 * @param {Element} element The Logo element
 */
function handleLogoClicked(element) {
    element.classList.toggle('fancy');
}

/**
 * Stretch Task 2
 * Handle toggling the search bar's modal
 */
function toggleSearchModal() {
    const dialog = document.body.querySelector('.search-dialog');

    const dialogOpen = dialog.getAttribute('isOpen') == 'true';
    
    if(dialogOpen)
        dialog.close();
    else
        dialog.showModal();

    dialog.setAttribute('isOpen', !dialogOpen);

}