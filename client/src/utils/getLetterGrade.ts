
/**
 * Converts a numeric score (0-100) to a letter grade.
 * @param value - numeric score attributed to a user's grade
 * @returns The corresponding letter grade, "Invalid" if out of range or "N/A" if not applicable.
 */
export function getLetterGrade(value: number)  {
  if(value < 0 || value > 100 || isNaN(value)) return "Invalid";
  
  switch (true) {
    case value >= 93: return 'A';
    case value >= 90 && value < 93: return 'A-';
    case value >= 87 && value < 90: return 'B+';
    case value >= 83 && value < 87: return 'B';
    case value >= 80 && value < 83: return 'B-';
    case value >= 77 && value < 80: return 'C+';
    case value >= 73 && value < 77: return 'C';
    case value >= 70 && value < 73: return 'C-';
    case value >= 67 && value < 70: return 'D+';
    case value >= 65 && value < 67: return 'D';
    case value >= 60 && value < 65: return 'D-';
    case value < 60: return 'F';
    default: return 'N/A';
  }
}