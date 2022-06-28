// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {

const greetings = ['I make portraits!', 'All is well']
  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

/** Fetches the current date from the server and adds it to the page. */
async function showHello() {
    const responseFromServer = await fetch('/hello');
    const messages = await responseFromServer.json();
    // pick a random mssg
    const message = messages[Math.floor(Math.random() * messages.length)];
  
    const messageContainer = document.getElementById('greeting');
    messageContainer.innerText = message;
  }

/** Fetches tasks from the server and adds them to the DOM. */
function loadComments() {
    fetch('/form-loader').then(response => response.json()).then((comments) => {
      const commentListElement = document.getElementById('comment-list');
      comments.forEach((comment) => {
        commentListElement.appendChild(createCommentElement(comment));
      })
    });
  }

/** Creates an element that represents a task, including its delete button. */
function createCommentElement(task) {
    const messageElement = document.createElement('li');
    messageElement.className = 'comment';
  
    const commentElement = document.createElement('span');
    commentElement.innerText = task.comment;

    const timeElement = document.createElement('span');
    timeElement.innerText = task.timestamp;

    const deleteButtonElement = document.createElement('button');
    deleteButtonElement.innerText = 'Delete';
    deleteButtonElement.addEventListener('click', () => {
        deleteComment(task);

    // Remove the task from the DOM.
    messageElement.remove();
  });

    messageElement.appendChild(commentElement);
    messageElement.appendChild(timeElement);
    messageElement.appendChild(deleteButtonElement);

    return messageElement;
  }

  /** Tells the server to delete the task. */
function deleteComment(task) {
    const params = new URLSearchParams();
    params.append('id', task.id);
    fetch('/delete-comment', {method: 'POST', body: params});
  }
  