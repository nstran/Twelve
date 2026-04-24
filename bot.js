const { Telegraf } = require('telegraf');
const OpenAI = require('openai');

// 1. Kết nối với 9Router của bạn
const openai = new OpenAI({
    baseURL: "http://localhost:20128/v1",
    apiKey: "sk-c402e88ebd6ba2e4-v9gym6-75e5f037" // Lấy ở mục Endpoint của 9Router
});

// 2. Kết nối với Telegram Bot
const bot = new Telegraf('8766554171:AAHiV8N_zE2KXlU87bSpExWffw0aXwV0meM'); // Lấy từ BotFather

bot.on('text', async (ctx) => {
    try {
        // Gửi câu hỏi từ Tele sang 9Router
        const chatCompletion = await openai.chat.completions.create({
            model: "cx/gpt-5.5", // Dùng chính con SuperDev bạn đã tạo
            messages: [{ role: "user", content: ctx.message.text }],
        });

        // Trả lời kết quả về Telegram
        ctx.reply(chatCompletion.choices[0].message.content);
    } catch (e) {
        ctx.reply("Lỗi: Kiểm tra xem 9Router có đang bật không sếp ơi!");
    }
});

bot.launch();
console.log("--- Bot đã online! Sếp có thể chat trên Telegram rồi ---");
