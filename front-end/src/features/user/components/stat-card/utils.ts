// @ts-check
// import toEmoji from "emoji-name-map";
// import wrap from "word-wrap";

/**
 * Encode string as HTML.
 *
 * @see https://stackoverflow.com/a/48073476/10629172
 *
 * @param {string} str String to encode.
 * @returns {string} Encoded string.
 */
const encodeHTML = (str: string) => {
  return str
    .replace(/[\u00A0-\u9999<>&](?!#)/gim, (i) => {
      return "&#" + i.charCodeAt(0) + ";";
    })
    .replace(/\u0008/gim, "");
};

/**
//  * Auto layout utility, allows us to layout things vertically or horizontally with
//  * proper gaping.
//  *
//  * @param {object} props Function properties.
//  * @param {string[]} props.items Array of items to layout.
//  * @param {number} props.gap Gap between items.
//  * @param {number[]?=} props.sizes Array of sizes for each item.
//  * @param {"column" | "row"?=} props.direction Direction to layout items.
//  * @returns {string[]} Array of items with proper layout.
//  */
// type flexLayoutProps = {
//   items: string[];
//   gap: number;
//   direction?: "column" | "row";
//   sizes?: number[];
// };

// const flexLayout = ({ items, gap, direction, sizes = [] }: flexLayoutProps) => {
//   let lastSize = 0;
//   // filter() for filtering out empty strings
//   return items.filter(Boolean).map((item, i) => {
//     const size = sizes[i] || 0;
//     let transform = `translate(${lastSize}, 0)`;
//     if (direction === "column") {
//       transform = `translate(0, ${lastSize})`;
//     }
//     lastSize += size + gap;
//     return `<g transform="${transform}">${item}</g>`;
//   });
// };

// /**
//  * Parse emoji from string.
//  *
//  * @param {string} str String to parse emoji from.
//  * @returns {string} String with emoji parsed.
//  */
// // const parseEmojis = (str: string) => {
// //   if (!str) throw new Error("[parseEmoji]: str argument not provided");
// //   return str.replace(/:\w+:/gm, (emoji) => {
// //     return toEmoji.get(emoji) || "";
// //   });
// // };

// /**
//  * Split text over multiple lines based on the card width.
//  *
//  * @param {string} text Text to split.
//  * @param {number} width Line width in number of characters.
//  * @param {number} maxLines Maximum number of lines.
//  * @returns {string[]} Array of lines.
//  */
// const wrapTextMultiline = (text: string, width = 59, maxLines = 3) => {
//   const fullWidthComma = "，";
//   const encoded = encodeHTML(text);
//   const isChinese = encoded.includes(fullWidthComma);

//   let wrapped = [];

//   if (isChinese) {
//     wrapped = encoded.split(fullWidthComma); // Chinese full punctuation
//   } else {
//     wrapped = wrap(encoded, {
//       width,
//     }).split("\n"); // Split wrapped lines to get an array of lines
//   }

//   const lines = wrapped.map((line: string) => line.trim()).slice(0, maxLines); // Only consider maxLines lines

//   // Add "..." to the last line if the text exceeds maxLines
//   if (wrapped.length > maxLines) {
//     lines[maxLines - 1] += "...";
//   }

//   // Remove empty lines if text fits in less than maxLines lines
//   const multiLineText = lines.filter(Boolean);
//   return multiLineText;
// };

/**
 * Retrieves num with suffix k(thousands) precise to 1 decimal if greater than 999.
 *
 * @param {number} num The number to format.
 * @returns {string|number} The formatted number.
 */
const kFormatter = (num: number) => {
  return Math.abs(num) > 999
    ? Math.sign(num) * parseFloat((Math.abs(num) / 1000).toFixed(1)) + "k"
    : Math.sign(num) * Math.abs(num);
};

export { encodeHTML, kFormatter };
