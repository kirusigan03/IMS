
# SeraniAI

SeraniAI is an AI-powered personal growth and wellness platform. It helps users track their learning, tasks, journal entries, mood, and conversations while providing personalized assistance through an AI chatbot.

The project includes a web application, a mobile application, a Node.js API, and a separate ChromaDB service for semantic search and Retrieval-Augmented Generation (RAG).

## Features

### Authentication and Account Management

- Email and password registration
- Email verification using a six-digit OTP
- Verification OTP resend support
- Login with email and password
- Google, GitHub, and Facebook OAuth login
- Short-lived JWT access tokens
- HTTP-only refresh-token cookies
- Refresh-token rotation and logout
- Remember-me session support
- Forgot-password OTP flow
- Password reset with OTP
- Protected routes and role-based authorization
- User profile retrieval and profile updates
- Profile image upload through Cloudinary
- Onboarding and personalization preferences
- Account deletion and logout

### Dashboard and Personal Progress

- Personalized dashboard greeting and account role badge
- Total journal count
- Daily task count and task completion status
- Completed lesson count
- AI interaction statistics
- Recent activity and recent lessons
- Journal, chat, and course trend charts
- Weekly progress report generation
- Weekly report PDF download
- Personalized recommendations
- Dismissible recommendations with temporary local persistence
- Daily reminders for journaling, courses, and learning streaks

### Journal and Wellbeing

- Create, view, edit, and delete journal entries
- Free-plan date-based journal experience
- Premium journal experience with advanced analytics
- Journal calendar navigation
- Search and filter journal entries
- Filter entries by mood
- Favorite journal entries
- Sort entries by newest, oldest, or mood
- List and grid journal views
- Mood selection and mood classification
- AI-generated journal summary and emotional tone
- AI-generated key themes and suggested actions
- Mood confidence and analysis source tracking
- Keyword-based fallback analysis when the AI service is unavailable
- Weekly and monthly journal insights
- Mood trend chart and mood distribution chart
- Journal streak tracking
- Refresh AI insight for an existing entry
- Journal entry PDF export

### Daily Wellness Tasks

- Mood-aware daily task recommendations
- Mindfulness, stress relief, emotional awareness, self-care, and focus categories
- Breathing exercises
- Timer-based tasks
- Guided tasks
- Action-based tasks
- Input and reflection tasks
- Easy, medium, and hard task difficulty levels
- Daily task assignment from the active task library
- Task completion tracking
- Task result and reflection storage
- XP calculation based on completed tasks
- Daily task streak tracking
- Local progress persistence and backend synchronization
- Admin task creation, editing, filtering, and deletion

### Courses and Learning

- Published course browsing
- Course search by title
- Category filters for mindfulness, stress, anxiety, healthcare, sleep, relationships, self-care, meditation, and emotional regulation
- Course levels: beginner, intermediate, and advanced
- Course enrollment
- Course and lesson recommendations from the AI assistant
- Continue-learning section
- Ordered video lessons
- Video URL and uploaded video support
- Lesson thumbnails
- Lesson completion and progress tracking
- Timestamp-based video notes
- Personal notes for each lesson
- Personal journal entries linked to lessons
- Course and lesson caching for faster access
- Admin course and lesson management
- Soft deletion for courses and lessons

### AI Chatbot

- Personalized AI conversations using user preferences and history
- Multiple chat sessions
- New conversation creation
- Chat history drawer
- Open previous chat sessions
- Delete one conversation
- Clear all conversation history
- Edit and resend a previous user message
- Optimistic message rendering with error rollback
- PDF and TXT file attachments
- Server-side PDF and text extraction
- Cloudinary storage for chat attachments
- Course recommendations inside assistant responses
- Mood-aware wellness task suggestions
- Positive-mood learning course suggestions
- Daily reminders inside chat responses
- Rich response rendering for headings, emphasis, lists, tables, code blocks, highlights, links, buttons, and images
- Copy assistant responses and code blocks
- AI-generated content download support
- Chat analysis panel with wellbeing score, mood trend, topic tags, activity heatmap, and interaction statistics

### Subscriptions and Payments

- Free, Pro, and Business plan support
- Personal and Business subscription handling
- PayHere checkout integration
- Monthly billing cycle
- Business plans with seat-based pricing
- Subscription status and payment history
- Subscription refresh and synchronization
- Payment retry support
- Subscription cancellation
- Enterprise premium access cancellation
- Subscription restrictions and upgrade prompts for free users
- Admin subscription listing, status management, and deletion

### Notifications

- In-app notification bell
- Automatic streak reminders
- Unread notification count
- Mark one notification as read
- Mark all notifications as read
- Clear one notification
- Clear all notifications
- Notification priority, category, links, and metadata

### Enterprise Management

- Enterprise workspace support
- Enterprise administrator role
- Enterprise member role
- Seat limit and seat usage tracking
- Invite users by email
- Accept enterprise invitations
- Edit enterprise member details
- Activate or deactivate members
- Remove enterprise members
- Revoke pending invitations
- Pending, accepted, expired, and active invite status
- Enterprise-managed premium access
- Enterprise manager screens on web and mobile

### Admin Portal

- Admin-only protected portal
- User management
- Course management
- Lesson management
- Wellness task management
- Subscription management
- Course dashboard statistics
- Course search and category management
- Notification access from the admin layout

### UI and Platform Experience

- Responsive React web application
- React Native and Expo mobile application
- Shared feature coverage across web and mobile
- Light and dark themes
- Persistent theme preference
- Responsive dashboard layouts for wide and narrow screens
- Loading, empty, error, and retry states
- Toast notifications on web
- Native alerts on mobile
- Browser deep links and mobile app deep links
- PDF generation and sharing on mobile
- Secure-area navigation based on authentication state

### Backend and Reliability

- REST API architecture with Express.js
- MongoDB persistence with Mongoose models
- Request validation using Zod
- Authentication and role middleware
- API rate limiting
- Helmet security headers
- CORS origin allow-list
- Response compression
- Health-check endpoint
- Redis-compatible caching utilities
- Course and lesson cache invalidation
- User-scoped data access for journals, chats, tasks, and notifications
- Upload type allow-lists and file-size limits
- Internal API-key protection for the ChromaDB service

## AI, LangChain, ChromaDB, and RAG

SeraniAI uses LangChain to build the AI response pipeline. The system combines the user's question, recent conversation history, user context, and relevant documents before generating a response.

Journal entries, chat messages, and course content are stored as embeddings in ChromaDB. When a user asks a question, the system performs semantic search across the relevant collections and retrieves the most related documents.

```text
User question
	|
	v
Search relevant journals, chat messages, and courses in ChromaDB
	|
	v
Combine retrieved context with conversation history and user preferences
	|
	v
Build a dynamic LangChain prompt
	|
	v
Generate a personalized AI response
```

This Retrieval-Augmented Generation (RAG) approach helps the chatbot answer using application-specific context instead of relying only on the model's general knowledge. User-specific collections are filtered by `userId` to keep personal context isolated.

## Architecture

```text
Web React/Vite app --------\
							> Node.js/Express backend --> MongoDB
Mobile React Native app --/              |
										 +--> LangChain + OpenAI
										 +--> ChromaDB service
										 +--> Cloudinary
										 +--> PayHere
```

### Applications and services

- `backend/`: Node.js and Express REST API, authentication, business logic, chat, courses, journals, tasks, billing, and notifications
- `web/`: React and Vite web application
- `mobile/`: React Native and Expo mobile application
- `chroma_service/`: FastAPI service that manages embeddings and ChromaDB collections

## Technology Stack

### Frontend

- React 19
- Vite
- React Router
- Tailwind CSS
- Framer Motion
- React Native and Expo

### Backend

- Node.js 20+
- Express.js
- MongoDB with Mongoose
- JWT authentication and HTTP-only refresh-token cookies
- Passport OAuth
- LangChain and OpenAI
- Multer and PDF parsing for document uploads

### AI and data services

- Python FastAPI
- ChromaDB
- Axios service integration
- Cloudinary for profile images and media
- PayHere for subscription payments

## Prerequisites

- Node.js 20 or newer
- npm
- Python 3.10 or newer
- MongoDB
- OpenAI API access for AI features

## Installation

Clone the repository and install dependencies for each application.

```bash
git clone <repository-url>
cd SeraniAI
```

### Backend

```bash
cd backend
npm install
npm run dev
```

The backend runs on `http://localhost:7001` by default.

### ChromaDB service

```bash
cd chroma_service
python -m venv .venv
```

Activate the virtual environment and install the Python dependencies:

```bash
# Windows PowerShell
.venv\Scripts\Activate.ps1

# macOS/Linux
source .venv/bin/activate

pip install -r requirements.txt
```

Start the service using the existing project scripts or the FastAPI entry point defined in the service.

### Web application

```bash
cd web
npm install
npm run dev
```

The Vite development server normally runs on `http://localhost:5173`.

### Mobile application

```bash
cd mobile
npm install
npm start
```

Use Expo Go or an Android/iOS emulator to run the mobile application.

## Environment Variables

Create a `.env` file inside `backend/`. At minimum, local development requires:

```env
NODE_ENV=development
PORT=7001
JWT_SECRET=your-jwt-secret
JWT_REFRESH_SECRET=your-refresh-secret
CONNECTION_STRING=your-mongodb-connection-string
OPENAI_API_KEY=your-openai-api-key
CHROMA_API_URL=http://localhost:5000
FRONTEND_URL=http://localhost:5173
```

Depending on the enabled features, configure the relevant email, OAuth, Cloudinary, encryption, PayHere, Redis, and ChromaDB service credentials as well. Never commit real secrets to the repository.

## Testing

Run backend tests:

```bash
cd backend
npm test
```

Run web tests and build:

```bash
cd web
npm run test:run
npm run build
```

Run web linting:

```bash
npm run lint
```

## Project Structure

```text
SeraniAI/
├── backend/
│   ├── controllers/
│   ├── models/
│   ├── routes/
│   ├── services/
│   ├── validations/
│   └── server.js
├── chroma_service/
│   ├── app/
│   ├── main.py
│   └── requirements.txt
├── mobile/
│   └── src/
├── web/
│   └── src/
└── README.md
```

## Security Notes

- Keep JWT, database, OpenAI, OAuth, payment, and email credentials in environment variables.
- Use HTTPS and secure cookies in production.
- Configure production CORS origins explicitly.
- Do not expose ChromaDB or internal service credentials publicly.

## License

This project is currently maintained as a private application. Add the appropriate license here before public distribution.
