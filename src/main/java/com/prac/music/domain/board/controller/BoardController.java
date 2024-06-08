package com.prac.music.domain.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.prac.music.domain.board.dto.BoardRequestDto;
import com.prac.music.domain.board.dto.BoardResponseDto;
import com.prac.music.domain.board.dto.UpdateRequestDto;
import com.prac.music.domain.board.dto.UpdateResponseDto;
import com.prac.music.domain.board.service.BoardService;
import com.prac.music.domain.user.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/boards")
public class BoardController {

	private final BoardService boardService;

	@PostMapping
	public ResponseEntity<BoardResponseDto> createBoard(@RequestPart(value = "files") List<MultipartFile> files,@RequestPart(value = "board") BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
		BoardResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser(),files);
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UpdateResponseDto> updateBoard(@PathVariable("id") Long id,@RequestPart(value = "files") List<MultipartFile> files,@RequestPart(value = "board") UpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
		UpdateResponseDto responseDto = boardService.updateBoard(id, requestDto, userDetails.getUser(),files);
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		boardService.deleteBoard(id, userDetails.getUser());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/list")
	public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
		List<BoardResponseDto> responseDtos = boardService.getAllBoard();
		return ResponseEntity.ok(responseDtos);
	}

	@GetMapping("/paging")
	public ResponseEntity<Page<BoardResponseDto>> paging(
		@Parameter(hidden = true) @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<BoardResponseDto> postsPages = boardService.paging(pageable);
		return ResponseEntity.ok(postsPages);
	}
}
