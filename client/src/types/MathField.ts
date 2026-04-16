import type { MathfieldElement } from "mathlive";
import type { DetailedHTMLProps, HTMLAttributes } from "react";


declare module "react/jsx-runtime" {
  namespace JSX {
    interface IntrinsicElements {
      "math-field": DetailedHTMLProps<
        HTMLAttributes<MathfieldElement>,
        MathfieldElement
      >;
    }
  }
}

export {}; // makes this file a module